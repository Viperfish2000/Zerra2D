#version 140

in vec2 position;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

void main() {
	gl_Position = projectionMatrix * transformationMatrix * viewMatrix * vec4(position, 0.0, 1.0);
}
