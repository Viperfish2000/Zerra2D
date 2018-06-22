#version 140

in vec2 position;
in mat4 modelViewMatrix;
in vec2 textureOffset;

out vec2 pass_TextureCoords;

uniform mat4 projectionMatrix;
uniform float numberOfRows;

void main() {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);

	vec2 textureCoords = position;
	textureCoords.y - 1.0 - textureCoords.y;
	textureCoords /= numberOfRows;
	pass_TextureCoords = textureCoords + textureOffset.yx;
}
