input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = strip(readline(file))
end

mutable struct Node
    val::Int
    next
end

function play_game(turn_n, arr)
    n = length(arr)
    root = Node(first(arr), nothing)
    prev_node = root
    d = Array{Node}(undef, 1, n)
    d[root.val] = root
    for val in arr[2:end]
        cur_node = Node(val, nothing)
        prev_node.next = cur_node
        prev_node = cur_node
        d[val] = cur_node
    end
    prev_node.next = root

    cur_cup_val = arr[1]
    for _ = 1:turn_n
        cur_cup_node = d[cur_cup_val]
        node_1 = cur_cup_node.next
        node_2 = node_1.next
        node_3 = node_2.next
        node_4 = node_3.next

        dest = cur_cup_val == 1 ? n : cur_cup_val - 1
        while in(dest, (node_1.val, node_2.val, node_3.val))
            dest = dest == 1 ? n : dest - 1
        end
        cur_cup_node.next = node_4
        node_dest = d[dest]
        node_right = node_dest.next
        node_dest.next = node_1
        node_3.next = node_right

        cur_cup_val = cur_cup_node.next.val
    end
    return d[1]
end


function solve1()
    arr = [parse(Int, c) for c in data]
    node = play_game(100, arr)
    ans  = []
    while node.next.val != 1
        push!(ans, node.next.val)
        node = node.next
    end
    return join(ans, "")
end

function solve2()
    arr = [parse(Int, c) for c in data]
    arr = vcat(arr, 10:1000000)
    node = play_game(10000000, arr)
    return node.next.val * node.next.next.val
    
end

println(@time solve1())
println(@time solve2())