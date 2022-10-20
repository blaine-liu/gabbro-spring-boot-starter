package io.github.aliothliu.gabbro;

import org.zalando.problem.StatusType;

import java.util.Objects;

public abstract class GabbroStatus implements StatusType {

    private final Long bizCode;

    private final String reasonPhrase;

    public GabbroStatus(Long bizCode, String reasonPhrase) {
        this.checkBizCode(bizCode);

        this.bizCode = bizCode;
        this.reasonPhrase = reasonPhrase;
    }

    protected void checkBizCode(Long code) {
        if (Objects.isNull(code)) {
            throw new NullPointerException("The code should not be null");
        }
    }

    public Long getBizCode() {
        return this.bizCode;
    }

    @Override
    public int getStatusCode() {
        return this.bizCodeToStatusCode(this.bizCode);
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    protected abstract int bizCodeToStatusCode(Long bizCode);
}
