input_path = joinpath(@__DIR__, "test.in")
t0, bus = open(input_path) do file
    t0 = parse(Int, readline(file))
    bus = split(readline(file), ',')
    (t0, bus)
end

function solve1()
    minval = 1e9
    minid = -1
    for val in bus
        if val != "x"
            val = parse(Int, val)
            if (t0%val) == 0
                return 0
            elseif val-(t0%val) < minval
                minval = val-(t0%val)
                minid = val 
            end
        end
    end
    return minval * minid 
end

function solve2()
    bus_valid = []
    ai  = []
    for (i,val) in enumerate(bus)
        if val != "x"
            val = parse(Int, val)
            push!(bus_valid, val)
            push!(ai, (val-((i-1)%val)) % val)
        end
    end
    N = reduce(*,bus_valid)
    yi = [div(N,val) for val in bus_valid]
    zi = [invmod(Int(yi[i]),val) for (i,val) in enumerate(bus_valid)]
    x = sum([ai[i]*yi[i]*zi[i] for i in 1:length(yi)])
    return x % N
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())