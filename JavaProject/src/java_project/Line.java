package java_project;

public class Line {
	private String fileAndLine;
	private String line;

	public Line(String fileName, long row, String line) {
		this.fileAndLine = String.format("%s - line%s, ", fileName,
				String.valueOf(row));
		this.setLine(line);
	}

	public String getId() {
		return fileAndLine;
	}

	public void setId(String id) {
		this.fileAndLine = id;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return String.format("%s %s", fileAndLine, line);
	}
}
