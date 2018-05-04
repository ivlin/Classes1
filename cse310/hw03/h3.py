#ivan lin
#python version 2.7
'''
Originally data stored in CSV file
f = open("dat.csv","r")
data = f.read()
f.close()
'''
data='''a,d,24
x,a,9
x,c,14
c,d,18
c,e,30
e,f,11
x,b,15
c,b,5
b,e,20
b,y,44
e,y,16
d,e,2
f,d,6
d,y,19
f,y,6'''

parsed_data=data.split("\n")
parsed_data=[item.split(",") for item in parsed_data]

node_dict = {}

for item in parsed_data:
    if item[0] not in node_dict:
        node_dict[item[0]]={}
    if item[1] not in node_dict:
        node_dict[item[1]]={}    
    node_dict[item[0]][item[1]]=int(item[2])
    node_dict[item[1]][item[0]]=int(item[2])

keys = list(node_dict.keys())

for from_node in node_dict:
    for key in keys:
        if key not in node_dict[from_node]:
            node_dict[from_node][key]=None
'''
for node in node_dict:
    print node
    print node_dict[node]
'''

def shortest_path(src,dst):
    return shortest_path_helper([src],dst,0)

    
def shortest_path_helper(src,dst,cost):
    if src[-1]==dst:
        return src,cost
    min_path=None
    min_cost=-1
    for neighbor in node_dict[src[-1]]:
        if node_dict[src[-1]][neighbor] is not None and neighbor not in src:
            #print src
            n_path ,n_cost = shortest_path_helper(src+[neighbor], dst, cost+node_dict[src[-1]][neighbor])
            if min_path==None or n_cost < min_cost:
                min_path=n_path
                min_cost=n_cost
    return min_path,min_cost

a,b=shortest_path('x','y')
print a
print b
