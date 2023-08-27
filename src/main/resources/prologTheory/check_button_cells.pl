%check_button_cells(@L, @C) verify if there is a button and at least a button_block or none of them for a specific color
check_button_cells(L, C) :- check_button_cells(L, C, 0, 0).

%base cases
check_button_cells([], _, 1, N2) :- N2 >= 1.
check_button_cells([], _, 0, 0).

%specific cell cases
check_button_cells([c(bt, _, _, C)|T], C, N, N2) :-
    N3 is N + 1,
    !,
    check_button_cells(T, C, N3, N2).
check_button_cells([c(bb, _, _, C)|T], C, N, N2) :-
    N4 is N2 + 1,
    !,
    check_button_cells(T, C, N, N4).
check_button_cells([_|T], C, N, N2) :- check_button_cells(T, C, N, N2).