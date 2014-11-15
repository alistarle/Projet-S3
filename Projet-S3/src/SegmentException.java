class SegmentException extends Exception {

	public SegmentException() {
		System.out.println("Format du segment incorrect.");
	}

	public SegmentException(Integer i) {
		System.out.println("Segment non présent dans la base.");
	}
}