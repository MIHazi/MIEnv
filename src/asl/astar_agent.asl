// Agent astar_agent in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

//At start we try to find a route from start pos.
+!start : start_pos(N) <-
	.print("start");
	+node_with_routes(N,0,-1);
	+node_weights(N,0);
	!before.

+!before :road([I,S,E,_,L]) & node_with_routes(S,_,_) & not node_with_routes(E,_,_) & end_pos(Endpos) & heuristic(E,EndPos,LH) &node_weights(S,Weight) <-
	+heur(H);
	.concat(H, L+LH+Weight, H);
	-+heur(H).
	
+!get_route :road([Roadindex,S,E,Speed,L]) & node_with_routes(S,Index,R) & not node_with_routes(E,_,_) & end_pos(Endpos) & heuristic(E,EndPos,LH)& heur(Heur)&node_weights(S,W) & .min(Heur, Min) & LH+L+W == Min <-
	+node_weights(E,W+L);
	for(.range(I,1,Index)) {
		+node_with_routes(E,I,Roadindex)
	}
	+node_with_routes(E,Index+1,Roadindex);
	if(E==Endpos) {
		!route_found;
	} else
	{
		!before;
	}.

+!route_found :node_with_routes(E, Index, Roadindex) <-
	+route_idx(1);
	for(.range(I,1,Index)) {
		+route(I,R)
	}
	has_route.
	
	
//If we are turning
+!turn_to(I,R) : true <-
	-route(I,R);		//We remove the route from the BB
	-route_idx(I);		//Increment the route idx
	+route_idx(I+1);
	turn(R).			//And execute the turn

//If we failed to turn
-!turn_to(I,R) : true  <-
	-route_idx(I);		//We decrement our idx
	+route_idx(I-1);
	+route(I,R).		//And add the last step of the route to the BB

//If we are on a node, and we have no further route, we are at our goal, and exit
+has_action : not route(_,_) <-
	.print("finished running");
	exit_sim.

//Otherwise, if we are on a node, and we already have an index, we execute the next step of our plan.
+has_action : route_idx(I) & route(I,R) <-
	!turn_to(I,R).

//If we don't have an index, we create one with 0
+has_action : route(0,R) <-
	+route_idx(0);
	!turn_to(0,R).