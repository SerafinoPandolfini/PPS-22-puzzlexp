% Define a predicate to check if a cell is a wall and has this format c(_, _, _, wall)
is_wall_cell(c(_, _, _, wall)).

% Define a predicate to filter a list of wall cells
filter_wall_cells([], []).
filter_wall_cells([H|T], Filtered) :-
    is_wall_cell(H),
    filter_wall_cells(T, Rest),
    Filtered = [H|Rest].
filter_wall_cells([_|T], Filtered) :-
    filter_wall_cells(T, Filtered).