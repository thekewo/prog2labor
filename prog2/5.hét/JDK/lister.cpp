#include <string>
#include <iostream>
#include <filesystem>
namespace fs = std::filesystem;

int main()
{
    std::string path = "/media/hallgato/prog2/5.hét/JDK/src";
    for (auto & p : fs::directory_iterator(path))
        std::cout << p << std::endl;
}