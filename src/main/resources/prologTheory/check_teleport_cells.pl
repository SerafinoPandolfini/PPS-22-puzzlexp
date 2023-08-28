%check_teleport_cells(@L) verify if there is a teleport and a teleport_destination or none of them
check_teleport_cells(L) :- check_teleport_cells(L, 0).

%base case
check_teleport_cells([], 0).

%cells type handling
check_teleport_cells([c(tl, _, _)|T], N) :- N2 is N + 1, !, check_teleport_cells(T, N2). 
check_teleport_cells([c(td, _, _)|T], N) :- N2 is N - 1, !, check_teleport_cells(T, N2).
check_teleport_cells([_|T], N) :- check_teleport_cells(T, N).