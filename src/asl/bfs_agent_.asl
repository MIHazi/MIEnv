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
	
//If we have no more unchecked child-nodes we step back to the parent of the current node
+!get_route_from(Idx,S) : route(Idx-1,P) <-
	if(Idx == 0){
		.print("search failed")
	}
	-route(Idx-1,P);
	!get_route_from(Idx-1,P).
	
+has_action : true. // TODO