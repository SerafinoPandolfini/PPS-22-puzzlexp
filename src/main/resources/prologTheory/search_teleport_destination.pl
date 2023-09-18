%search_button_block( +L, -X, -Y) filter the specified list and returns the coordinates teleport destination
search_teleport_destination([c(td, X, Y) | T], X, Y):- !.
search_teleport_destination([_ | T], X, Y) :- search_teleport_destination(T, X, Y).

