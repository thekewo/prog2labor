
/* 
   build: g++ jdk.cpp -o jdk -lboost_system -lboost_filesystem 
   run: ./jdk <"absolute path">
   */

#include "boost/filesystem/operations.hpp" // includes boost/filesystem/path.hpp
#include "boost/filesystem/fstream.hpp"    
#include "boost/filesystem.hpp"
#include <iostream>                        

using namespace boost::filesystem;
using namespace std;

string getExt(path p){
	if (p.has_extension()){
        	return p.extension().string();
        }
    return "";
}
int counter(0);
void dirent(path p){
	

	try {

    if (exists(p)) {
      
      if (is_regular_file(p)){
        cout << p << " size is " << file_size(p) << '\n';
       
       if (getExt(p) == ".java"){
       		counter++;
       		
       }
    }

      else if (is_directory(p)) {
        cout << p << " is a directory containing:\n";

        for (directory_entry& x : directory_iterator(p)){
        
          cout << "    " << x.path() << '\n'; 
          dirent(x.path());
      		
        }
      }
     
    }
    

  }

  catch (const filesystem_error& ex)
  {
    cout << ex.what() << '\n';
  }

}

int main(int argc, char* argv[]){
	path p;
	
	if ( argv[1] != NULL){
		p = argv[1];
	}
	else {
		p =  boost::filesystem::current_path();
	}
	
	std::cout << "Current path is : " << boost::filesystem::current_path()<< std::endl;

	dirent(p);
	cout<< "JDK contains "<<counter<<" .java files"<<endl;
	
	return 0;
}
