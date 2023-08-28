% non_repeating_cells_counter(@L, +MaxX, +MaxY) check if the cells have valid positions

valid_positions([], _, _).
valid_positions([c(_, X, Y)|T], MaxX, MaxY) :-
    X >= 0, X =< MaxX,
    Y >= 0, Y =< MaxY,
    valid_positions(T, MaxX, MaxY).