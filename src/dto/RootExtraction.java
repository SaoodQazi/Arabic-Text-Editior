package dto;

public class RootExtraction {
	private int id;
	private int pageId;
	private String word;
	private String root;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String pos) {
		this.root = pos;
	}
}
