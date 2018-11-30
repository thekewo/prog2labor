#include <iostream>
#include "mlp.hpp"
#include <png++/png.hpp>
#include <fstream>

//g++ main.cpp -o main -lpng -std=c++11
//./main mandel.png

using namespace std;

int main (int argc, char **argv)
{
  png::image <png::rgb_pixel> png_image (argv[1]);

  int size = png_image.get_width() * png_image.get_height();

  Perceptron* p = new Perceptron (3, size, 256, size);//itt a 4. paraméter 1 volt

  double* image = new double[size];
  double* image_er = new double[size];//ezt nem fogjuk bántani, 6. tétel beugró

  fstream kif("ki.txt",ios_base::out);

  for(int i {0}; i<png_image.get_width(); ++i)
    for(int j{0}; j<png_image.get_height();++j){
      image[i*png_image.get_width()+j] = png_image[i][j].red;
      image_er[i*png_image.get_width()+j] = png_image[i][j].red;//6. tétel beugró
    }

  double value = (*p) (image);

  std::cout << value << std::endl;

  //saját kép készítése
  int szelesseg = 600, magassag = 600, iteraciosHatar = 1000;
  png::image <png::rgb_pixel> kep(szelesseg, magassag);

  for(int i{0}; i<png_image.get_width(); ++i)
    for(int j{0}; j<png_image.get_height(); ++j) {
      kep.set_pixel(i, j, png::rgb_pixel(1, 1, 255));
    }

  kep.write("sajat.png");
  //cout << "xd" << endl;

  //8. tétel beugró
  for(int i{0}; i<png_image.get_width(); ++i)
    for(int j{0}; j<png_image.get_height(); ++j) {
      png_image[i][j].red = image_er[i*png_image.get_width()+j];//6. tétel, itt az eredetiből készítjük a képet
      png_image[i][j].blue = image_er[i*png_image.get_width()+j];
      png_image[i][j].green = image_er[i*png_image.get_width()+j];
    }

  png_image.write("tetel8.png");
  cout << endl << "Tetel 8: Kimeneti kep mentve! " << endl << endl;

  //p->save(kif);//alapértelmezett mentés
  p->kiir();

  delete p;
  delete [] image;
  delete [] image_er;

  kif.close();
}
