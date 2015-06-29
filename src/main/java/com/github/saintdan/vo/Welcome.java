package com.github.saintdan.vo;

/**
 * Welcome controller vo.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
public class Welcome {

	private final long id;

	private final String content;

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Welcome(long id, String content) {
		this.id = id;
		this.content = content;
	}

}
