#include <iostream>
#include "mlp.hpp"
#include "png.hpp"
#include <fstream>

//g++ main.cpp -o main -lpng -std=c++11
//./main mandel.png

using namespace std;

int main (int argc, char **argv)
{
  png::image <png::rgb_pixel> png_image (argv[1]);

  int size = png_image.get_width() * png_image.get_height();

  Perceptron* p = new Perceptron (3, size, 256, size);

  double* image = new double[size];
  

  for(int i {0}; i<png_image.get_width(); ++i)
    for(int j{0}; j<png_image.get_height();++j){
      image[i*png_image.get_width()+j] = png_image[i][j].red;
    ;
    }

  double value = (*p) (image);

  
//Saját kép

for (size_t y=0;y<png_image.get_width();y++){
	for(size_t x=0;x<png_image.get_height();x++){
		png_image[y][x].green=resultImage[y*png_image.get_width()+x];
}}

png_image.write("kesz.png");

delete p;
delete[] image;

}
