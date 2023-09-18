%check_pressure_cells(@L) verify if there is a pressure_plate and at least a pressure_plate_block or none of them
check_pressure_cells(L) :- check_pressure_cells(L, 0, 0).

%base cases
check_pressure_cells([], 1, N2) :- N2 >= 1.
check_pressure_cells([], 0, 0).

%cells type handling
check_pressure_cells([c(pp, _, _)|T], N, N2) :- N3 is N + 1, !, check_pressure_cells(T, N3, N2). 
check_pressure_cells([c(pb, _, _)|T], N, N2) :- N4 is N2 + 1, !, check_pressure_cells(T, N, N4).
check_pressure_cells([_|T], N, N2) :- check_pressure_cells(T, N, N2).