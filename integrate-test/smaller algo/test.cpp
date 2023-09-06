#include <vector>
#include <iostream>
#include <map>
#include <unordered_map>

std::multimap<size_t,std::vector<std::pair<size_t,size_t>>> make_bins(const std::unordered_map<size_t,size_t>& objects, size_t K) {
    std::multimap<size_t,std::vector<std::pair<size_t,size_t>>> bins;
    bins.insert({K,{}});
    for(auto& item: objects) { //ID-Weight
        if (item.second >= K) {
            bins.insert({0,{item}});
        } else {
            auto bin = bins.lower_bound(item.second); // we have a bin that has space.
            if (bin != bins.end()) {
                auto node = bins.extract(bin);
                node.key() = node.key() - item.second;
                node.mapped().push_back(item);
                bins.insert(std::move(node));
            } else {
                bins.insert({K-item.second,{item}});
            }
        }
    }
    return bins;
}


// Test data..
int main() {

    size_t max_capacity = 500;
    std::vector<size_t> obj = {2,42,2,32,21,32,19,2,4,2,4,5,2,5,6,6,96,37,34,54,80,55,84,20,74,50,56,95,40,93,28,37,17,101,28,82,55,58,42,101,29,54,88,73,4,37,22,25,71,93,99,51,14,95,82,90,99,66,63,58,14,73,7,6,98,63,60,79,60,49,91,58,68,52,6,51,69,82,36,71,7,28,88,42,80,81,42,32,52,93,53,3,20,15,8,211,91,52,38,46,79,60,76,86,22,50,101,70,92,43,27,6,33,19,15,30,99,87,52,59,38,92,71,85,32,76,21,10,82,96,61,30,9,75,39,14,6,31,28,75,61,33,85,42,2,41,43,64,3,68,60,77,39,61,63,38,25,66,93,30,75,71,31,23,67,20,93,2,4,45,51,81,23,25,27,2,17,66,17,32,26,31,35,54,2,5,65,51,31,84,42,36,2,50,46,22,53,50,84,84,65,51,72,54,99,46,90,44,60,2,40,38,80,26,95,2,94,56,66,31,25,18,89,42,59,3,86,50,97,18,58,79,100,32,82,94,66,87,61,32,85,87,48,76,7,24,33,19,64,15,60,10,47,7,44,80,100,72,39,61,17,83,48,100,79,52,20,26,66,50,64,26,44,85,22,68,62,72,9,16,2,35,35,14,15,9,8,33,93,50,21,30,75,51,64,40,27,23,34,83,29,35,58,17,81,7,40,43,62,35,10,121,95,30,92,71,16,16,43,16,76,40,33,6,26,23,68,66,80,92,101,52,11,60,71,18,65,11,42,14,5,49,2,89,80,23,121,5,9,53,58,23,2,10,98,19,29,38,91,57,51,9,40,76,62,96,83,35,96,64,4,46,40,5,28,35,26,57,101,78,63,59,3,68,61,23,61,101,70,76,37,74,46,43,30,66,32,73,22,6,49,33,23,91,111,39,76,98,7,78,72,50,43,92,56,15};

    std::unordered_map<size_t,size_t> u;
    size_t x = 1002;
    for (auto &i : obj) {
        u[x++] = i;
    }

    auto bins = make_bins(u,max_capacity); // 69,99
    for (auto& bin: bins) {
        size_t wt = 0;
        std::cout << max_capacity - bin.first << ": [";
        for (auto& i: bin.second) {
            wt += i.second;
            std::cout << '{' << i.first << ',' << i.second << '}';
        }
        std::cout << "]\n";
    }
    return 0;
}
