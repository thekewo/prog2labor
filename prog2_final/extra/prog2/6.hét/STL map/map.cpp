
#include <iostream>
#include <map>
#include <algorithm>
#include <vector>

using namespace std;

int main(){
	map<int, int>mymap;
	std::map<int,int>::iterator it = mymap.begin();
	mymap[1] = 40;
	mymap[2] = 43;
	mymap[3] = 14;
	mymap[4] = 71;
	mymap[5] = 16;
	mymap[6] = 87;

	cout<<"EREDETI SORREND"<<endl;
	cout<<"Kulcs\tÉRTÉK"<<endl;

	for( it=mymap.begin(); it!=mymap.end(); ++it ){
		cout<< it->first<<"\t"<<it->second<<endl;
	}

	vector<pair<int, int>> ordered;

	for(auto i : mymap){
		pair<int, int> p {i.first, i.second};
                        ordered.push_back ( p );
	}
	 std::sort (
                std::begin ( ordered ), std::end ( ordered ),
        [ = ] ( auto && p1, auto && p2 ) {
                return p1.second < p2.second; //növekvõ sorrend
        }
        );

	 cout<<"\n\nRENDEZETT SORREND"<<endl;
	 cout<<"Kulcs\tÉRTÉK"<<endl;
	 for( auto i : ordered){
	 	cout<<i.first<<"\t"<<i.second<<endl;
	 }
	return 0;
}