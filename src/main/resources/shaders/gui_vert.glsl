#version 140

in vec2 position;

out vec2 pass_TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform vec4 textureOffsets;

void main() {
	gl_Position = projectionMatrix * transformationMatrix * vec4(position, 0.0, 1.0);

	pass_TextureCoords = position;
	pass_TextureCoords.x *= textureOffsets.z;
	pass_TextureCoords.y *= textureOffsets.w;
	pass_TextureCoords.x += textureOffsets.x;
	pass_TextureCoords.y += textureOffsets.y;
}
