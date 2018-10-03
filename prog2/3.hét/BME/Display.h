// File: Display.h
class Display : public Product
{
	int inchWidht;
	int inchHeight;
	protected:
		void printParams(std::ostream& os) const;
};
