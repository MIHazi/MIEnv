// Agent bfs_agent_ in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

//At start we try to find a route from start pos.
+!start : start_pos(N) <-
	.print("start");
	!get_route_from(0,N).

//When the node we are on (while searching) is our goal, we found our route (in the route belief)
+!get_route_from(_,S) : end_pos(S) <-
	.print("End found : ", S);
	has_route.

//If we have roads from our current position, which we didn't already check
+!get_route_from(Idx,S) : .member(M, roads) & M == [I,S,E,_,_] & not has_passed(E) <-
	.print("route from ",S);
	+route(Idx,I);				//We add the road leading to it to our route
	+has_passed(E);				//Indicate that we passed the node it's leading to
	!get_route_from(Idx+1,E).	//And try to find a route from that node
	
//If we have no more unchecked child-nodes
+!get_route_from(Idx,S) : route(Idx-1,P) <-
	if(Idx == 0){
		.print("search failed")
	}
	-route(Idx-1,P);			//We step back to the parent of the current node
	!get_route_from(Idx-1,P).	//And try to find another route from there

//If we are turning
+!turn_to(I,R) : true <-
	-route(I,R);		//We remove the route from the BB
	-route_idx(I);		//Increment the route idx
	+route_idx(I+1);
	turn(R).			//And execute the turn

//If we failed to turn
-!turn_to(I,R) : true <-
	-route_idx(I);		//We decrement our idx
	+route_idx(I-1).
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