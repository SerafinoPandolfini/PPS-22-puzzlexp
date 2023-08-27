% non_repeating_cells_counter(@L, ?N) return the number of cells that have different positions

%base case
non_repeating_cells_counter([], 0).

%skip the counter if there is at least another cell with the same position
non_repeating_cells_counter([c(_, X, Y)|T], N) :-
    member(c(_, X, Y), T),
    !,
    non_repeating_cells_counter(T, N).
    
%count a cell if there are no other cells left with the same position     
non_repeating_cells_counter([_|T], N) :-
    non_repeating_cells_counter(T, N2),
    N is N2 + 1.