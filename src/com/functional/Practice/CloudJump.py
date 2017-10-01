

# n,k = map(int,input().strip().split())
# c=list(map(int,input().strip().split()))[::k]
# print(100-sum([i+(i==1)+1 for i in c]))


def test(name, addr):
    print (name, addr)

def main():
    n, k = map(int, input().strip().split())
    c = list(map(int, input().strip().split()))[::k]
    print(100-sum([i+(i == 1)+1 for i in c]))

if __name__ == "__main__":
    #main()
    test( addr='tr', namE='R12')
    print ("arajaj")
