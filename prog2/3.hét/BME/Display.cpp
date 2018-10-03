//File: Display.cpp
void Display::printParams(std::ostream& os) const
{
	Product::printParams(os);
	os << ", " << "InchWidth: " << inchWidht;
	os << ", " << "InchHeight: " << inchHeight;
}