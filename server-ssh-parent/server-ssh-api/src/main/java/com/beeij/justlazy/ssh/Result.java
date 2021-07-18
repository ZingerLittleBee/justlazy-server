package com.beeij.justlazy.ssh;

import lombok.*;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-17
 * @since 2021-07-17 16:09
 */
@Getter
@Setter
@ToString
public class Result<T> {
    private Boolean success;
    private T data;

    public Result(Boolean success) {
        this.success = success;
        this.data = null;
    }

    public Result(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static Result<?> NoDataResult(Boolean success) {
        return new Result<>(success);
    }

    public static Result<?> FaultResult() {
        return Result.NoDataResult(false);
    }

    public static Result<?> OkResult(Object data) {
        return new Result<>(true, data);
    }

    public boolean okOrNot() {
        return this.success;
    }

    public boolean hasData() {
        return this.data != null;
    }

}
