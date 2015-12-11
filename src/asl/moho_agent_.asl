// Agent moho_agent_ in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : start_pos(N) <-
	!get_route(N).
	
+!get_route(S) : end_pos(S) <-
	has_route.
	
+!get_route(S) : road([I,S,E,_,_]) & not weight(E,_) & 
		node([E,X,Y]) & end_pos(E2) & node([E2,X2,Y2]) & 
		current_best(S,_,B) & weight(B,W) <-
	+weight(E,sqrt((X2-X)*(X2-X)+(Y2-Y)*(Y2-Y)));
	?weight(E,W2);
	if(W2 < W){
		-+current_best(S,I,W2);
	}
	!get_route(S).
	
+!get_route(S) : road([_,S,E,_,_]) & current_best(S,_,_) <-
	!get_route(E).
	
+!get_route(S) : road([I,S,E,_,_]) & not current_best(S,_,_) &
		node([E,X,Y]) & end_pos(E2) & node([E2,X2,Y2]) <-
	+weight(E,sqrt((X2-X)*(X2-X)+(Y2-Y)*(Y2-Y)));
	?weight(E,W2);
	+current_best(S,I,W2);
	!get_route(S).

+!turn_to(I) : true <-
	turn(I).
	
-!turn_to(_) : true <-
	.wait("+has_action", 50).

+has_action : current_pos(P) & end_pos(P) <-
	.print("finished");
	exit_sim.

+has_action : current_pos(P) & current_best(P,I,_) <-
	!turn_to(I).