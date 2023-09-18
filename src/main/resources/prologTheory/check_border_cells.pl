%check_border_cells(@C, +MaxX, +MaxY, ?L) return the basic cells on the border (links)
check_border_cells(C, MaxX, MaxY, L) :-
    %return the cells on the border
    filter_border_cells(C, MaxX, MaxY, BC),
    %check the basic cells on the border
    check_cells(BC, L).


%base case
filter_border_cells([], _, _, []).

%case if a cell is on the border
filter_border_cells([c(C, X, Y)|T], MaxX, MaxY, [c(C, X, Y) | T2]) :-
    (X = 0 ; X = MaxX ; Y = 0 ; Y = MaxY),
    filter_border_cells(T, MaxX, MaxY, T2), !.

%case cell not on border
filter_border_cells([_|T], MaxX, MaxY, T2) :-
    filter_border_cells(T, MaxX, MaxY, T2).


%base case
check_cells([], []).

%case wall cell
check_cells([c(wl, _, _)|T], L) :-
    check_cells(T, L).

%case basic cell
check_cells([c(bs, X, Y)|T], [c(bs, X, Y)|T2]) :-
    check_cells(T, T2).