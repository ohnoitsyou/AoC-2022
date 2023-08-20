package dev.dayoung

import org.junit.jupiter.api.Test

class NineTest {

    @Test
    fun diagonal() {
        val rope = Nine.Rope(2)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.U)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.R)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.R)
        rope.knots[1] = rope.knotStep(rope.knots[1], rope.knots[0])
        assert(rope.knots[0] == 2 to 1)
        assert(rope.knots[1] == 1 to 1)
    }
    @Test
    fun up() {
        val rope = Nine.Rope(2)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.U)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.U)
        rope.knots[1] = rope.knotStep(rope.knots[1], rope.knots[0])
        assert(rope.knots[0] == 0 to 2)
        assert(rope.knots[1] == 0 to 1)
    }
    @Test
    fun down() {
        val rope = Nine.Rope(2)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.D)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.D)
        rope.knots[1] = rope.knotStep(rope.knots[1], rope.knots[0])
        assert(rope.knots[0] == 0 to -2)
        assert(rope.knots[1] == 0 to -1)
    }

    @Test
    fun left() {
        val rope = Nine.Rope(2)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.L)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.L)
        rope.knots[1] = rope.knotStep(rope.knots[1], rope.knots[0])
        assert(rope.knots[0] == -2 to 0)
        assert(rope.knots[1] == -1 to 0)
    }
    @Test
    fun right() {
        val rope = Nine.Rope(2)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.R)
        rope.knots[0] = rope.headStep(Nine.Rope.Direction.R)
        rope.knots[1] = rope.knotStep(rope.knots[1], rope.knots[0])
        assert(rope.knots[0] == 2 to 0)
        assert(rope.knots[1] == 1 to 0)
    }
}