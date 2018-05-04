f = open("dat.csv","r")
data = f.read()
f.close()
parsed_data=data.split("\n")
parsed_data=[item.split(",") for item in parsed_data]

node_dict = {}

for item in parsed_data:
    if item[0] not in node_dict:
        node_dict[item[0]]={}
    if item[1] not in node_dict:
        node_dict[item[1]]={}    
    node_dict[item[0]][item[1]]=int(item[2])

print(node_dict)
    
def build_tree_from_djikstra(start, node_dict):
    connected=[start]
    branches={}
    while len(connected)<len(node_dict):
        min_node_src=None
        min_node_dst=None
        for cur_node in connected:
            node=node_dict[cur_node]
            for key in node:
                if key not in connected and (min_node_src is None or node[key] < node_dict[min_node_src][min_node_dst]):
                    min_node_dst=key
                    min_node_src=cur_node
        connected.append(min_node_dst)
        branches[min_node_dst]=min_node_src
    return branches
    
def find_shortest_path(start, dest, node_dict):
    shortest_path_tree = build_tree_from_djikstra(start,node_dict)
    #print(shortest_path_tree)
    path=[dest]
    cost=0
    for item in path:
        if item in shortest_path_tree:
            path.append(shortest_path_tree[item])
            cost = cost + node_dict[shortest_path_tree[item]][item]
    path.reverse()
    return path, cost


path, cost = find_shortest_path('x','y',node_dict)
print("Path:" + str(path))
print("Cost:" + str(cost))
