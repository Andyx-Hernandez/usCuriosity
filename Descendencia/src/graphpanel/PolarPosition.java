package graphpanel;

public class PolarPosition {
	// Coordenadas polares de un objeto dentro de un CirclePanel
	// crown indica la corona, o el radio en que se encuentra
	// sector indica el sector, o el ángulo en que se encuentra
	// width indica cuántos sectores ocupa el objeto
	private int crown, sector;
	private int width;

	public PolarPosition(int crown, int sector) {
		this.crown = crown;
		this.sector = sector;
		width = 1;
	}
	
	public PolarPosition(int crown, int sector, int width) {
		this.crown = crown;
		this.sector = sector;
		this.width = width;
	}

	public int getCrown() {
		return crown;
	}

	public void setCrown(int crown) {
		this.crown = crown;
	}

	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}	
}
