package com.eiviv.fdfs.cmd;

import java.io.IOException;
import java.io.OutputStream;

import com.eiviv.fdfs.context.Context;
import com.eiviv.fdfs.model.Result;

public class CloseCmd extends AbstractCmd<Boolean> {
	
	@Override
	protected RequestBody getRequestBody() {
		return new RequestBody(Context.FDFS_PROTO_CMD_QUIT);
	}
	
	@Override
	protected OutputStream getOutputStream() {
		return null;
	}
	
	@Override
	protected byte getResponseCmdCode() {
		return 0;
	}
	
	@Override
	protected long getFixedBodyLength() {
		return 0;
	}
	
	@Override
	protected Result<Boolean> callback(com.eiviv.fdfs.cmd.AbstractCmd.Response response) throws IOException {
		return new Result<Boolean>(response.getCode(), response.isSuccess());
	}
	
}
