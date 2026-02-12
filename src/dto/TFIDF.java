package dto;

public class TFIDF {
	private int tfidfId;
	private int fileId;
	private int tfidfScore;

	public int getTfidfScore() {
		return tfidfScore;
	}

	public void setTfidfScore(int tfidfScore) {
		this.tfidfScore = tfidfScore;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getTfidfId() {
		return tfidfId;
	}

	public void setTfidfId(int tfidfId) {
		this.tfidfId = tfidfId;
	}

}
