input_path = joinpath(@__DIR__, "test.in")
graph, graph_inv = open(input_path) do file
    graph = Dict([])
    graph_inv = Dict([])
    for line in eachline(file)
        spl1 = split(line, "contain ")
        node = spl1[1][1:end-6]
        graph[node] = []

        if spl1[2] == "no other bags."
            continue
        else
            for next_node in split(spl1[2], ", ")
                spl2 = split(next_node, " ")
                cnt = parse(Int, spl2[1])
                next_next_node = spl2[2] * " " * spl2[3]
                if !haskey(graph_inv,next_next_node)
                    graph_inv[next_next_node] = []
                end
                push!(graph_inv[next_next_node], [node,cnt])
                push!(graph[node], [next_next_node,cnt])
            end
        end
    end
    (graph, graph_inv)
end


function solve1()
    ans = 0
    lst_nodes = ["shiny gold"]
    visited_set = Set{String}([])
    push!(visited_set, "shiny gold")
    while (length(lst_nodes) >0)
        node = pop!(lst_nodes)
        if haskey(graph_inv,node)
            for next_node in graph_inv[node]
                if !in(next_node[1],visited_set)
                    push!(visited_set, next_node[1])
                    ans += 1
                    push!(lst_nodes, next_node[1])
                end
            end
        end
    end
    return ans
end

function solve2()
    ans = 0
    lst_nodes = [["shiny gold",1]]
    visited_set = Set{String}([])
    push!(visited_set, "shiny gold")
    while (length(lst_nodes) >0)
        node,cnt_node = pop!(lst_nodes)
        for next_node in graph[node]
            if !in(next_node[1],visited_set)
                ans += next_node[2]*cnt_node
                push!(lst_nodes, [next_node[1], cnt_node*next_node[2]])
            end
        end
    end
    return ans
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())