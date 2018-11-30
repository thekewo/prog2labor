#include <png++/png.hpp>
#include <iostream>
#include <cstdarg>
#include <map>
#include <iterator>
#include <cmath>
#include <random>
#include <limits>
#include <fstream>

//g++ mlp.hpp Perceptron.cpp -o perc -lpng
//std::ofstream of("output2.txt");

class Perceptron
{
public:
  Perceptron(int nof, ...)
  {
    size_t sum = 0;
    n_layers = nof;
    units = new double *[n_layers];
    sum+=sizeof(double*)*n_layers;
   // std::cout<<sum<<std::endl;
    n_units = new int[n_layers];
    //std::cout<<"n_units"<<sizeof(n_units)<<" "<<sizeof(double*)*n_layers<<std::endl;
    sum+=sizeof(int)*n_layers;
    va_list vap;

    va_start(vap, nof);

    for (int i{0}; i < n_layers; ++i)
    {
      n_units[i] = va_arg(vap, int);

      if (i)
      {
        units[i] = new double[n_units[i]];
        sum += sizeof(n_units[i])*sizeof(double);
      }
    }

    va_end(vap);

    weights = new double **[n_layers - 1];
    sum+=sizeof(double**)*(n_layers-1);
#ifndef RND_DEBUG
    std::random_device init;
    std::default_random_engine gen{init()};
#else
    std::default_random_engine gen;
#endif

    std::uniform_real_distribution<double> dist(-1.0, 1.0);

    for (int i{1}; i < n_layers; ++i)
    {
      weights[i - 1] = new double *[n_units[i]];
      sum+=sizeof(double*)*sizeof(n_units[i]);
      for (int j{0}; j < n_units[i]; ++j)
      {
        weights[i - 1][j] = new double[n_units[i - 1]];
        sum += sizeof(double)*sizeof(n_units[i-1]);
        for (int k{0}; k < n_units[i - 1]; ++k)
        {
          weights[i - 1][j][k] = dist(gen);
          sum += sizeof(weights[i - 1][j][k]);

          //              of << weights[i-1][j][k];
        }
        //sum+= sizeof(weights[i-1][j]);
      }
      //sum+= sizeof(weights[i-1]);
    }
    std::cout <<" lefoglalt: " <<sum/1024/1024 <<" MB"<< std::endl;
  }

  Perceptron(std::fstream &file)
  {
    file >> n_layers;

    units = new double *[n_layers];
    n_units = new int[n_layers];

    for (int i{0}; i < n_layers; ++i)
    {
      file >> n_units[i];

      if (i)
        units[i] = new double[n_units[i]];
    }

    weights = new double **[n_layers - 1];

    for (int i{1}; i < n_layers; ++i)
    {
      weights[i - 1] = new double *[n_units[i]];

      for (int j{0}; j < n_units[i]; ++j)
      {
        weights[i - 1][j] = new double[n_units[i - 1]];

        for (int k{0}; k < n_units[i - 1]; ++k)
        {
          file >> weights[i - 1][j][k];
        }
      }
    }
  }

  double sigmoid(double x)
  {
    return 1.0 / (1.0 + exp(-x));
  }

  double operator()(double image[])
  {

    units[0] = image;

    for (int i{1}; i < n_layers; ++i)
    {

#ifdef CUDA_PRCPS

      cuda_layer(i, n_units, units, weights);

#else

#pragma omp parallel for
      for (int j = 0; j < n_units[i]; ++j)
      {
        units[i][j] = 0.0;

        for (int k = 0; k < n_units[i - 1]; ++k)
        {
          units[i][j] += weights[i - 1][j][k] * units[i - 1][k];
        }

        units[i][j] = sigmoid(units[i][j]);
      }

#endif
    }

    return sigmoid(units[n_layers - 1][0]);
  }

  void learning(double image[], double q, double prev_q)
  {
    double y[1]{q};

    learning(image, y);
  }

  void learning(double image[], double y[])
  {
    //( *this ) ( image );

    units[0] = image;

    double **backs = new double *[n_layers - 1];

    for (int i{0}; i < n_layers - 1; ++i)
    {
      backs[i] = new double[n_units[i + 1]];
    }

    int i{n_layers - 1};

    for (int j{0}; j < n_units[i]; ++j)
    {
      backs[i - 1][j] = sigmoid(units[i][j]) * (1.0 - sigmoid(units[i][j])) * (y[j] - units[i][j]);

      for (int k{0}; k < n_units[i - 1]; ++k)
      {
        weights[i - 1][j][k] += (0.2 * backs[i - 1][j] * units[i - 1][k]);
      }
    }

    for (int i{n_layers - 2}; i > 0; --i)
    {

#pragma omp parallel for
      for (int j = 0; j < n_units[i]; ++j)
      {

        double sum = 0.0;

        for (int l = 0; l < n_units[i + 1]; ++l)
        {
          sum += 0.19 * weights[i][l][j] * backs[i][l];
        }

        backs[i - 1][j] = sigmoid(units[i][j]) * (1.0 - sigmoid(units[i][j])) * sum;

        for (int k = 0; k < n_units[i - 1]; ++k)
        {
          weights[i - 1][j][k] += (0.19 * backs[i - 1][j] * units[i - 1][k]);
        }
      }
    }

    for (int i{0}; i < n_layers - 1; ++i)
    {
      delete[] backs[i];
    }

    delete[] backs;
  }

  ~Perceptron()
  {
    for (int i{1}; i < n_layers; ++i)
    {
      for (int j{0}; j < n_units[i]; ++j)
      {
        delete[] weights[i - 1][j];
      }

      delete[] weights[i - 1];
    }

    delete[] weights;

    for (int i{0}; i < n_layers; ++i)
    {
      if (i)
        delete[] units[i];
    }

    delete[] units;
    delete[] n_units;
  }

  void save(std::fstream &out)
  {
    out << " "
        << n_layers;

    for (int i{0}; i < n_layers; ++i)
      out << " " << n_units[i];

    for (int i{1}; i < n_layers; ++i)
    {
      for (int j{0}; j < n_units[i]; ++j)
      {
        for (int k{0}; k < n_units[i - 1]; ++k)
        {
          out << " "
              << weights[i - 1][j][k];
        }
      }
    }
  }
  void sum()
  {
    int count = 0;
    value = 0;
    for (int i = 0; i < n_layers; i++)
    {
      value = 0;
      for (int j = 0; j < n_units[i]; j++)
      {
        value += units[i][j];
        count++;
      }
      std::cout << value / n_units[i];
    }
  }

private:
  Perceptron(const Perceptron &);
  Perceptron &operator=(const Perceptron &);

  int value = 0;
  int n_layers;
  int *n_units;
  double **units;
  double ***weights;
};
int main(int argc, char **argv)
{
  png::image<png::rgb_pixel> png_image(argv[1]);
  int size = png_image.get_width() * png_image.get_height();
  std::cout<<" png meret "<<size<<std::endl;
  //std::cout<<size;
  Perceptron *p = new Perceptron(3, size, 256, size);
  //double d[] = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
  double *image = new double[size];

  for (int i{0}; i < png_image.get_width(); i++)
    for (int j{0}; j < png_image.get_height(); j++)
      image[i * png_image.get_width() + j] = png_image[i][j].red;

  double value = (*p)(image);
  
   

  for (int i{0}; i < png_image.get_width(); i++)
    for (int j{0}; j < png_image.get_height(); j++){
     png_image[i][j].green = image[i*png_image.get_width()+j];
   // png_image[i][j].red = image[i*png_image.get_width()+j];
    //png_image[i][j].blue = image[i*png_image.get_width()+j];
    }

    png_image.write("out.png");



  //  std::fstream of("output.txt");
  // p->save(of);
  //p->sum();
  delete p;
  delete[] image;

  return 0;
}
