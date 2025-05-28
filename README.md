count-min-sketch
- probabilistic data structure
- use multiple hash function
- for each input increments counter in 2D array
- estimate the count of query item as the minimum value from all hashed count

`ε` (epsilon)
- error in estimate of frequencies

`δ` (delta)
- probability that error exceed epsilon

array
- `w` = e/ε
- `d` = ln(1/δ)