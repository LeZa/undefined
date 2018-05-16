package com.build.api.Spi;

import java.beans.Encoder;
import java.util.Base64;

public abstract class CodecSet {
	 public abstract Encoder getEncoder(String encodingName);
	 public abstract Base64.Decoder getDecoder(String encodingName);
}
