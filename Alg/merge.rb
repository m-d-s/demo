class Merge
  attr_accessor :to_sort

  def initialize to_sort
      @to_sort = to_sort
  end

  def merge_sort p, r
    if p < r
      q = ((p + r ) * 0.5).floor
      merge_sort p, q
      merge_sort q+1, r
      merge p, q, r
    end
  end
  
  def merge p, q, r
    left = []
    right = []
    if p < r
      (p..q).each { |i| left << @to_sort[i] }
      (q+1..r).each { |i| right << @to_sort[i] }
      left << 4000;
      right << 4000;
      i = p
      x = 0
      y = 0
      (p..r).each {
        if left[x] < right[y]  
          @to_sort[i] = left[x];
          x += 1;
        else  
          @to_sort[i] = right[y]; 
          y +=1; 
        end 
        i += 1 
      }
    end
  end

end


alg = Merge.new [3,5,8,2,4,9]
alg.merge_sort(0, 5)
alg.to_sort.each { |x| puts x }
