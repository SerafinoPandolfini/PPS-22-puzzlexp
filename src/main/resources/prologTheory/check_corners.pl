% Define a predicate to check if the four corners (TopLeft, TopRight, BottomLeft, BottomRight) are completed or have cells that aren't walls'
check_corners(Filtered, TL, TR, BL, BR).

% Check if an element is a member of the list
is_member(Element, List) :- member(Element, List).

% Define the specific elements to check for the top-left corner
check_top_left(c(wl, 1, 1, wall)).
check_top_left(c(wl, 1, 2, wall)).
check_top_left(c(wl, 2, 1, wall)).

% Define the specific elements to check for the top-right corner
check_top_right(c(wl, 1, 3, wall)).
check_top_right(c(wl, 1, 2, wall)).
check_top_right(c(wl, 2, 3, wall)).

% Define the specific elements to check for the bottom-left corner
check_bottom_left(c(wl, 3, 1, wall)).
check_bottom_left(c(wl, 3, 2, wall)).
check_bottom_left(c(wl, 2, 1, wall)).

% Define the specific elements to check for the bottom-right corner
check_bottom_right(c(wl, 3, 3, wall)).
check_bottom_right(c(wl, 3, 2, wall)).
check_bottom_right(c(wl, 2, 3, wall)).

% Define a predicate to check if the top-left corner is completed
% Completed = (1,1)-(1,2)-(2,1) are walls
top_left_corner(Filtered, TL).
filter_wall_cells([H|T], Filtered) :-
    is_wall_cell(H),
    filter_wall_cells(T, Rest),
    Filtered = [H|Rest].
filter_wall_cells([_|T], Filtered) :-
    filter_wall_cells(T, Filtered).
    angolo in alto a sinistra