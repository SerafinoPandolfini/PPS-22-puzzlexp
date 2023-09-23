% Define a predicate to check if a cell is a wall and has this format c(_, _, _, wall)
is_wall_cell(c(_, _, _, wall)).

% Define a predicate to filter a list of wall cells
filter_wall_cells([], []).% :- !, check_corner(Filtered, B, S).
filter_wall_cells([H|T], Filtered) :-
    is_wall_cell(H),
    !,
    filter_wall_cells(T, Rest),
    Filtered = [H|Rest].
filter_wall_cells([_|T], Filtered) :-
    filter_wall_cells(T, Filtered).


% Define a predicate to check if the four corners (TopLeft, TopRight, BottomLeft, BottomRight) are completed
% Each corner with its related cells
%TopLeft = [c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)].
%TopRight = [c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)].
%BottomLeft = [c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)].
%BottomRight = [c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)].

check_list([], _ ).
check_list([H | T], L) :-
    member(H, L),
    !,  % Cut here to prevent backtracking if H is a member
    check_list(T, L).
%check_list(_, L, false).

%13) TopRight-BottomLeft-BottomRight-TopLeft C
check_corner(L, c) :-
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    !.

%4) TopLeft-TopRight-BottomLeft INW
check_corner(L, inw) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    !.

%5) TopLeft-TopRight-BottomRight INE
check_corner(L, ine) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%8) TopLeft-BottomLeft-BottomRight ISW
check_corner(L, isw) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%12) TopRight-BottomLeft-BottomRight ISE
check_corner(L, ise) :-
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%2)TopRight-BottomRight W
check_corner(L, w) :-
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%3)TopLeft-BottomLeft E
check_corner(L, e) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    !.

%10) BottomLeft-BottomRight N
check_corner(L, n) :-
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%11) TopLeft-TopRight S
check_corner(L, s) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    !.

%6) TopRight SW
check_corner(L, sw) :-
    check_list([c(wl, 1, -1, wall), c(wl, 0, -1, wall), c(wl, 1, 0, wall)], L),
    !.

%7) TopLeft SE
check_corner(L, se) :-
    check_list([c(wl, -1, -1, wall), c(wl, 0, -1, wall), c(wl, -1, 0, wall)], L),
    !.

%1) BottomLeft NE
check_corner(L, ne) :-
    check_list([c(wl, -1, 0, wall), c(wl, -1, 1, wall), c(wl, 0, 1, wall)], L),
    !.

%9) BottomRight NW
check_corner(L, nw) :-
    check_list([c(wl, 1, 0, wall), c(wl, 0, 1, wall), c(wl, 1, 1, wall)], L),
    !.

%14) There are no complete corners A
check_corner(L, a).

filter_wall_and_check_corners(List, CornerType) :-
    filter_wall_cells(List, Filtered),
    check_corner(Filtered, CornerType).