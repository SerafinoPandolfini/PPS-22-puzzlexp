%search_button_block( +L, -X, -Y, @C) filter the specified list and returns the coordinates of the button blocks of a specified color
search_button_block([], [], [], C).
search_button_block([c(bb, X1, Y1, C) | T], [X1 | T2], [Y1 | T3],  C) :- !, search_button_block(T, T2, T3, C).
search_button_block([_ | T], X, Y, C) :- search_button_block(T, X, Y, C).