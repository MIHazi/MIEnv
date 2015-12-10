// Agent astar_agent in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

//At start we try to find a route from start pos.
+!start : start_pos(N) <-
	.print("start");
	+node_with_routes(N,R);
	+node_weights(N,0);
	!get_route(N).

//When the node we are on (while searching) is our goal, we found our route (in the route belief)
/*+!get_route_from(_,S) : end_pos(S) <-
	.print("End found : ", S);
	has_route.
	*/
	
+!get_route :road([I,S,E,_,L]) & node_with_routes(S,_) & not node_with_routes(E,R) & end_pos(Endpos) & heuristic(E,EndPos,LH) & heuristic(_,_,LH1) &LH<LH1 & node_weights(S,W)<-
	+node_weights(E,W+L);
	+node_with_routes(E,R+road([I,S,E,_,L]));
	if(E==Endpos) {
		!route_found;
	} else
	{
		!get_route;
	}.
	
+!route_found :node_with_routes(E,R) <-
	+route(R);
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