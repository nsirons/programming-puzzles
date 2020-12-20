input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = Dict()
    cur_id = nothing
    tile = zeros(Int,(10,10))
    r = 1
    for line in vcat(readlines(file), [""])
        if length(line) == 0
            data[cur_id] = tile
            cur_id = nothing
            tile = zeros(Int, (10,10))
            r = 1
        elseif cur_id === nothing
            m = match(r"Tile (\d+):", line)
            cur_id = parse(Int, m[1])
        else
            tile[r,:] = [Int(c=='#') for c in line]
            r += 1
        end
    end
    (data)
end

connections = Dict([])
connections_edge = Dict([])
corners = []

function solve1()
    for (key,tile) in data
        edge0 = tile[1,:]
        edge1 = tile[10,:]
        edge2 = tile[:,1]
        edge3 = tile[:,10]
        edges = [edge0, edge1, edge2, edge3]
        for (other_key, other_tile) in data
            if key != other_key
                other_edge0 = other_tile[1,:]
                other_edge1 = other_tile[10,:]
                other_edge2 = other_tile[:,1]
                other_edge3 = other_tile[:,10]
                other_edges = [other_edge0, other_edge1, other_edge2, other_edge3]
                ok = false
                common_edge = []
                for (a,b) in Iterators.product(edges, other_edges) 
                    if (a == b) || (a[end:-1:1] == b)
                        ok = true
                        common_edge = b
                        break
                    end
                end
                if ok
                    if haskey(connections, key)
                        push!(connections[key],other_key)
                        connections_edge[(key,other_key)] = common_edge
                        connections_edge[(other_key,key)] = common_edge
                    else
                        connections[key] = [other_key,]
                        connections_edge[(key,other_key)] = common_edge
                        connections_edge[(other_key,key)] = common_edge
                    end
                end
            end
        end
    end
    ans = 1
    for (key,val) in connections
        if (length(val) == 2)
            ans *= key
            push!(corners, key)
        end
    end
    return ans
end

function solve2()
    image_ref = zeros(Int,(12,12))
    image_ref[1,1] = corners[1]
    visited = Set([])
    for i in 1:12
        for j in 1:11
            node = image_ref[i,j]
            push!(visited, node)
            if i == 1 && j == 1 # pick any
                next_node = connections[node][1]
                image_ref[2,1] = connections[node][2]
            elseif i == 1 || i == 12 # top/bot edge: pick not visited edge or corner
                next_node = [cand for cand in connections[node] if length(connections[cand]) <= 3 && !in(cand,visited)]
                @assert length(next_node) == 1
                next_node = next_node[1]
            elseif j == 1  # left edge: pick right cell, and next left edge
                next_node = [cand for cand in connections[node] if !in(cand, visited)]
                @assert length(next_node) == 2
                if length(connections[next_node[1]]) == 4
                    next_node, image_ref[i+1,1]  = next_node
                else
                    image_ref[i+1,1], next_node = next_node
                end
            else
                next_node = [cand for cand in connections[node] if !in(cand,visited) && sum((1 for other_node in connections[cand] if in(other_node, visited))) == 2]
                @assert length(next_node) == 1
                next_node = next_node[1]
            end
            image_ref[i,j+1] = next_node
        end
        push!(visited, image_ref[i,end])
    end

    # println("-- image with tile id --")
    # for ii = 1:12
    #     println(image_ref[ii,:])
    # end

    image_true = zeros(Int, (12*10, 12*10))
    image_final = zeros(Int, (12*8, 12*8))

    # find right orientation of first block
    right_ref, bottom_ref = connections[corners[1]]
    
    if image_ref[1,2] != right_ref
        right_ref, bottom_ref = bottom_ref, right_ref
    end
    right_common_line = connections_edge[(corners[1], right_ref)]
    bottom_common_line = connections_edge[(corners[1], bottom_ref)]

    cor = data[corners[1]]
    right = data[right_ref]
    bot = data[bottom_ref]

    edge0 = cor[1,:]
    edge1 = cor[10,:]
    edge2 = cor[:,1]
    edge3 = cor[:,10]

    if edge0 == right_common_line
        cor = rotr90(cor)
    elseif edge0[end:-1:1] == right_common_line
        cor = rotr90(cor[:,end:-1:1])
    elseif edge1 == right_common_line
        cor = rotl90(cor[:,end:-1:1])
    elseif edge1[end:-1:1] == right_common_line
        cor = rotl90(cor)
    elseif edge2 == right_common_line
        cor = cor[:,end:-1:1]
    elseif edge2[end:-1:1] == right_common_line
        cor = rot180(cor)
    elseif edge3 == right_common_line
        cor = cor
    elseif edge3[end:-1:1] == right_common_line
        cor = cor[end:-1:1,:]
    else
        @assert false
    end

    if !(cor[10,:] == bottom_common_line || cor[10,end:-1:1,:] == bottom_common_line)
        image_ref = image_ref[end:-1:1,:]
        corner = image_ref[1,1]

        right_ref, bottom_ref = connections[corner]
        
        if image_ref[1,2] != right_ref
            right_ref, bottom_ref = bottom_ref, right_ref
        end
        right_common_line = connections_edge[(corner, right_ref)]
        bottom_common_line = connections_edge[(corner, bottom_ref)]

        cor = data[corner]
        right = data[right_ref]
        bot = data[bottom_ref]

        edge0 = cor[1,:]
        edge1 = cor[10,:]
        edge2 = cor[:,1]
        edge3 = cor[:,10]

        if edge0 == right_common_line
            cor = rotr90(cor)
        elseif edge0[end:-1:1] == right_common_line
            cor = rotr90(cor[:,end:-1:1])
        elseif edge1 == right_common_line
            cor = rotl90(cor[:,end:-1:1])
        elseif edge1[end:-1:1] == right_common_line
            cor = rotl90(cor)
        elseif edge2 == right_common_line
            cor = cor[:,end:-1:1]
        elseif edge2[end:-1:1] == right_common_line
            cor = rot180(cor)
        elseif edge3 == right_common_line
            cor = cor
        elseif edge3[end:-1:1] == right_common_line
            cor = cor[end:-1:1,:]
        else
            @assert false
        end
    end

    @assert (cor[10,:] == bottom_common_line || cor[10,end:-1:1,:] == bottom_common_line)     

    image_true[1:10,1:10] = cor
    image_final[1:8,1:8] = cor[2:9,2:9]
    for i=1:12, j=1:12
        if j == 1 && i == 1
            continue
        elseif j==1  # align with top
            top_common_line = image_true[1 + (i-1)*10-1,1 + (j-1)*10: 1+j*10-1] 
            
            cor = data[image_ref[i,j]]
            edge0 = cor[1,:]
            edge1 = cor[10,:]
            edge2 = cor[:,1]
            edge3 = cor[:,10]

            if edge0 == top_common_line
                cor = cor
            elseif edge0[end:-1:1] == top_common_line
                cor = cor[:,end:-1:1]
            elseif edge1 == top_common_line
                cor = cor[end:-1:1,:]
            elseif edge1[end:-1:1] == top_common_line
                cor = rot180(cor)
            elseif edge2 == top_common_line
                cor = rotr90(cor[end:-1:1,:])
            elseif edge2[end:-1:1] == top_common_line
                cor = rotr90(cor)
            elseif edge3 == top_common_line
                cor = rotl90(cor)
            elseif edge3[end:-1:1] == top_common_line
                cor = rotl90(cor[end:-1:1,:])
            else
                @assert false
            end
            image_true[ 1 + (i-1)*10:(i)*10,1 + (j-1)*10:(j)*10] = cor
            image_final[1 + (i-1)* 8:(i)* 8,1 + (j-1)*8:(j)*8]  = cor[2:9,2:9]
        else  # allign with left
            cor = data[image_ref[i,j]]
            left_common_line = image_true[1 + (i-1)*10:(i)*10,1 + (j-1)*10-1] 
            edge0 = cor[1,:]
            edge1 = cor[10,:]
            edge2 = cor[:,1]
            edge3 = cor[:,10]

            if edge0 == left_common_line
                cor = rotl90(cor[:,end:-1:1])
            elseif edge0[end:-1:1] == left_common_line
                cor = rotl90(cor)
            elseif edge1 == left_common_line
                cor = rotr90(cor)
            elseif edge1[end:-1:1] == left_common_line
                cor = rotr90(cor[:,end:-1:1])
            elseif edge2 == left_common_line
                cor = cor
            elseif edge2[end:-1:1] == left_common_line
                cor = cor[end:-1:1,:]
            elseif edge3 == left_common_line
                cor = cor[:,end:-1:1]
            elseif edge3[end:-1:1] == left_common_line
                cor = rot180(cor)
            else
                @assert false
            end
            image_true[1 + (i-1)*10:(i)*10,1 + (j-1)*10:(j)*10] = cor
            image_final[1 + (i-1)*8:(i)*8,1 + (j-1)*8:(j)*8] = cor[2:9,2:9]
        end

    end

    # image_true = image_true[end:-1:1,:]
    # for ii =1:size(image_true)[1]
    #     if (ii-1)%8 == 0
    #         println("")
    #     end
    #     for jj =1:size(image_true)[2]
    #         if (jj-1)%8 == 0
    #             print(" | ")
    #         end
    #         print(image_true[ii,jj])
    #     end
    #     println("")
    # end

    monster = [
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   ",
    ]

    mask = ones(size(image_final))
    hh,ww = size(monster)[1], length(monster[1])
    monster_m = zeros(Int,(hh,ww))
    for i=1:hh,j=1:ww
        monster_m[i,j] = monster[i][j]=='#' ? 1 : 0
    end

    h,w = size(image_final)
    ok = false
    monster_cells = sum(monster_m)
    
    for flipasd=1:2
        for r=1:4
            for i=1:h-3
                for j=1:w-20
                    if sum(monster_m .* image_final[i:i+3-1,j:j+20-1]) == monster_cells
                        mask[i:i+3-1,j:j+20-1] -= monster_m
                        ok = true
                    end
                end
            end
            if ok
                mask = mask .> 0
                return sum(image_final .* mask)
            end
            image_final = rotl90(image_final)
        end
        image_final = image_final[end:-1:1,:]
    end
end


println("part 1: ", @time solve1())
println("part 2: ", @time solve2())