#include <iostream>
#include <map>
#include <vector>
#include <algorithm>

typedef std::pair<std::string,int> pair;

int main()
{
    // a rendezendõ map, string kulcsból és int értékekbõl áll
    std::map<std::string,int> map = {
        {"two", 2}, {"one", 1}, {"five", 5}, {"four", 4}, {"three", 3}
    };

    // párokat tartalmazó üres vektor
    std::vector<pair> vec;

    // a map egészét (az elejétõl a végéig) a vec vektorba másolja át
    std::copy(map.begin(), map.end(), std::back_inserter(vec));

    // a vektor rendezése a párok második értéke alapján
    std::sort(vec.begin(), vec.end(),
            [](const pair& l, const pair& r) {
                return l.second < r.second;
            });

    // a rendezett vektor kiíratása range-based for looppal
    for (auto const &pair: vec) {
        std::cout << '{' << pair.first << " , " << pair.second << '}' << std::endl;
    }

    return 0;
}
