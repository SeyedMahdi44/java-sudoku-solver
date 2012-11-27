(deftemplate Big
    (slot r)
    (slot c)
    (slot b)
    (slot v)
)

(deftemplate Small
    (slot r)
    (slot c)
    (slot b)
    (slot v)
)

(defrule candidate-elim
    (declare (salience 2))
    
    ?x <- (Small (r ?r) (c ?c) (b ?b) (v ?v))
    
    (or
        (Big  (r ?r) (c ?c)) 
        (Big  (r ?r) (v ?v)) 
        (Big  (c ?c) (v ?v)) 
        (Big  (b ?b) (v ?v))
    )
    
    (not (Big  (r ?r) (c ?c) (v ?v)) )
    
    =>
    (retract ?x)
)

(defrule naked-single
    (Small (r ?r) (c ?c) (b ?b) (v ?v))
    (not (exists (Small (r ?r) (c ?c) (v ~?v))))
   =>
    (assert (Big (r ?r) (c ?c) (b ?b) (v ?v)))
)

(defrule hidden-single-row
    (Small (r ?r) (c ?c) (b ?b) (v ?v))
    (not (exists (Small (r ?r) (c ~?c) (v ?v)) ))
    =>
    (assert (Big (r ?r) (c ?c) (b ?b) (v ?v)))
)

(defrule hidden-single-col
    (Small (r ?r) (c ?c) (b ?b) (v ?v))
    (not (exists (Small (r ~?r) (c ?c) (v ?v)) ))
    =>
    (assert (Big (r ?r) (c ?c) (b ?b) (v ?v)))
)

(defrule hidden-single-box
    (Small (r ?r) (c ?c) (b ?b) (v ?v))
    (not (exists (Small (c ~?c) (b ?b) (v ?v)) ))
    (not (exists (Small (r ~?r) (b ?b) (v ?v)) ))
    =>
    (assert (Big (r ?r) (c ?c) (b ?b) (v ?v)))
)

(defrule block-row
    ?x <- (Small (r ?r) (b ?b1) (v ?v))
    
         (exists (Small (r  ?r) (b ?b2&~?b1) (v ?v)))
    (not (exists (Small (r ~?r) (b ?b2     ) (v ?v))))
    =>
    (retract ?x)
)

(defrule block-col
    ?x <- (Small (c ?c) (b ?b1) (v ?v))
    
         (exists (Small (c  ?c) (b ?b2&~?b1) (v ?v)))
    (not (exists (Small (c ~?c) (b ?b2     ) (v ?v))))
    =>
    (retract ?x)
)
