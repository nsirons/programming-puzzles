input_path = joinpath(@__DIR__, "test.in")
cmd = open(input_path) do file
    cmd = []
    for line in readlines(file)
        m = match(r"(\w)(\d+)", line)
        push!(cmd, [m[1], parse(Int,m[2])])
    end
    (cmd)
end

function solve1()
    cur_angle = 0
    cns = 0.0
    cew = 0.0
    for (dir, unit) in cmd
        if dir == "N"
            cns += unit
        elseif dir == "S"
            cns -= unit
        elseif dir == "E"
            cew += unit
        elseif dir == "W"
            cew -= unit
        elseif  dir == "R"
            cur_angle += unit
        elseif dir == "L"
            cur_angle -= unit
        elseif dir == "F"
            cns -= unit*sind(cur_angle)
            cew += unit*cosd(cur_angle)
        end
    end
    return abs(round(cns)) + abs(round(cew)) 
end


function solve2()
    cur_angle = 0
    cns = 0.0
    cew = 0.

    wpns = 1
    wpew = 10
    for (dir, unit) in cmd
        if dir == "N"
            wpns += unit
        elseif dir == "S"
            wpns -= unit
        elseif dir == "E"
            wpew += unit
        elseif dir == "W"
            wpew -= unit
        elseif  dir == "R"
            wpew, wpns = (wpew)*cosd(-unit) -(wpns)*sind(-unit), (wpns)*cosd(-unit) + (wpew)*sind(-unit)
        elseif dir == "L"
            wpew, wpns = -(wpns)*sind(unit) + (wpew)*cosd(unit), (wpns)*cosd(unit) + (wpew)*sind(unit)
        elseif dir == "F"
            cns += wpns * unit
            cew += wpew * unit
        end
    end
    return abs(round(cns)) + abs(round(cew)) 
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())