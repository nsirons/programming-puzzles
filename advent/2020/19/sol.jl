input_path = joinpath(@__DIR__, "test.in")
(graph, msgs) = open(input_path) do file
    graph = Dict([])
    msgs = []
    switch = false
    for line in readlines(file)
        if length(line) == 0
            switch = true
        elseif switch
            push!(msgs, line)
        else
            spl = split(line, ":")
            graph[parse(Int,spl[1])] = split(strip(replace(spl[2], '"'=>"")), " ")
        end
        
    end
    (graph, msgs)
end


function resolve(c)
    if tryparse(Int, c) !== nothing
        c=parse(Int, c)
    end
    if (c== "a") || (c == "b")
        return c
    elseif c == "|"
        return "|"
    else
        return "(" * generate(c) * ")" 
    end 
end


function generate(node)
    return join((resolve(c) for c in graph[node]),"")
end


function solve1()
    pattern = Regex("^"*generate(0)*"\$")
    part1 = 0
    for msg in msgs
        if match(pattern, msg) !== nothing
            part1 += 1
        end
    end
    return part1
end


function solve2()
    ans = 0
    unknown_msg = Set(msgs)
    for a=1:20
        try
            pattern = Regex("^("*generate(42) * ")+("*generate(42)*"){$a}("*generate(31)*"){$a}\$")
            for msg in copy(unknown_msg)
                if match(pattern, msg) !== nothing
                    ans += 1
                    delete!(unknown_msg, msg)
                end
            end
        catch e
            # println(e)
            break
        end
        
    end
    return ans
end

println("part 1: ",@time solve1())
println("part 2: ",@time solve2())