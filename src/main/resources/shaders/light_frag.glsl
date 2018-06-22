#version 330

out vec4 out_Color;

uniform vec3 lightColor;
uniform float lightSize;

void main() {
	out_Color = vec4(lightColor, 1.0);
}
