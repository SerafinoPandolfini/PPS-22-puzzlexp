% cells_counter(@L, -N) return the number of cells
cells_counter([], 0).
cells_counter([_|T], N):-cells_counter(T, N2), N is N2 + 1.