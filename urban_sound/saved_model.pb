.
ë	Ä	
9
Add
x"T
y"T
z"T"
Ttype:
2	
x
Assign
ref"T

value"T

output_ref"T"	
Ttype"
validate_shapebool("
use_lockingbool(
8
Const
output"dtype"
valuetensor"
dtypetype
.
Identity

input"T
output"T"	
Ttype
o
MatMul
a"T
b"T
product"T"
transpose_abool( "
transpose_bbool( "
Ttype:

2
e
MergeV2Checkpoints
checkpoint_prefixes
destination_prefix"
delete_old_dirsbool(

NoOp
M
Pack
values"T*N
output"T"
Nint(0"	
Ttype"
axisint 
C
Placeholder
output"dtype"
dtypetype"
shapeshape:
A
Relu
features"T
activations"T"
Ttype:
2		
o
	RestoreV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0
l
SaveV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0
H
ShardedFilename
basename	
shard

num_shards
filename
N

StringJoin
inputs*N

output"
Nint(0"
	separatorstring 
s

VariableV2
ref"dtype"
shapeshape"
dtypetype"
	containerstring "
shared_namestring "train*1.4.12b'v1.4.1-0-g438604f'Ź"
d
IPlaceholder*
dtype0*
shape:˙˙˙˙˙˙˙˙˙*'
_output_shapes
:˙˙˙˙˙˙˙˙˙
Z
zerosConst*
valueB*    *
_output_shapes

:*
dtype0
u
W
VariableV2*
dtype0*
_output_shapes

:*
shape
:*
	container *
shared_name 

W/AssignAssignWzeros*
_output_shapes

:*
_class

loc:@W*
T0*
validate_shape(*
use_locking(
T
W/readIdentityW*
T0*
_output_shapes

:*
_class

loc:@W
T
zeros_1Const*
valueB*    *
_output_shapes
:*
dtype0
m
b
VariableV2*
dtype0*
_output_shapes
:*
shape:*
	container *
shared_name 

b/AssignAssignbzeros_1*
_output_shapes
:*
_class

loc:@b*
T0*
validate_shape(*
use_locking(
P
b/readIdentityb*
T0*
_output_shapes
:*
_class

loc:@b
s
MatMulMatMulIW/read*
transpose_a( *
transpose_b( *
T0*'
_output_shapes
:˙˙˙˙˙˙˙˙˙
L
addAddMatMulb/read*
T0*'
_output_shapes
:˙˙˙˙˙˙˙˙˙
@
OReluadd*
T0*'
_output_shapes
:˙˙˙˙˙˙˙˙˙
"
initNoOp	^W/Assign	^b/Assign
u
Assign/valueConst*1
value(B&"  ?   @  @   @  ŕ@   A*
_output_shapes

:*
dtype0

AssignAssignWAssign/value*
_output_shapes

:*
_class

loc:@W*
T0*
validate_shape(*
use_locking(
_
Assign_1/valueConst*
valueB"  ?  ?*
_output_shapes
:*
dtype0

Assign_1AssignbAssign_1/value*
_output_shapes
:*
_class

loc:@b*
T0*
validate_shape(*
use_locking(
P

save/ConstConst*
valueB Bmodel*
_output_shapes
: *
dtype0

save/StringJoin/inputs_1Const*<
value3B1 B+_temp_a2a2d9ef322f49bcbbb67a1e2cd22998/part*
_output_shapes
: *
dtype0
u
save/StringJoin
StringJoin
save/Constsave/StringJoin/inputs_1*
N*
	separator *
_output_shapes
: 
Q
save/num_shardsConst*
value	B :*
_output_shapes
: *
dtype0
\
save/ShardedFilename/shardConst*
value	B : *
_output_shapes
: *
dtype0
}
save/ShardedFilenameShardedFilenamesave/StringJoinsave/ShardedFilename/shardsave/num_shards*
_output_shapes
: 
e
save/SaveV2/tensor_namesConst*
valueBBWBb*
_output_shapes
:*
dtype0
g
save/SaveV2/shape_and_slicesConst*
valueBB B *
_output_shapes
:*
dtype0
{
save/SaveV2SaveV2save/ShardedFilenamesave/SaveV2/tensor_namessave/SaveV2/shape_and_slicesWb*
dtypes
2

save/control_dependencyIdentitysave/ShardedFilename^save/SaveV2*
T0*
_output_shapes
: *'
_class
loc:@save/ShardedFilename

+save/MergeV2Checkpoints/checkpoint_prefixesPacksave/ShardedFilename^save/control_dependency*

axis *
N*
_output_shapes
:*
T0
}
save/MergeV2CheckpointsMergeV2Checkpoints+save/MergeV2Checkpoints/checkpoint_prefixes
save/Const*
delete_old_dirs(
z
save/IdentityIdentity
save/Const^save/control_dependency^save/MergeV2Checkpoints*
T0*
_output_shapes
: 
e
save/RestoreV2/tensor_namesConst*
valueBBW*
_output_shapes
:*
dtype0
h
save/RestoreV2/shape_and_slicesConst*
valueB
B *
_output_shapes
:*
dtype0

save/RestoreV2	RestoreV2
save/Constsave/RestoreV2/tensor_namessave/RestoreV2/shape_and_slices*
dtypes
2*
_output_shapes
:

save/AssignAssignWsave/RestoreV2*
_output_shapes

:*
_class

loc:@W*
T0*
validate_shape(*
use_locking(
g
save/RestoreV2_1/tensor_namesConst*
valueBBb*
_output_shapes
:*
dtype0
j
!save/RestoreV2_1/shape_and_slicesConst*
valueB
B *
_output_shapes
:*
dtype0

save/RestoreV2_1	RestoreV2
save/Constsave/RestoreV2_1/tensor_names!save/RestoreV2_1/shape_and_slices*
dtypes
2*
_output_shapes
:

save/Assign_1Assignbsave/RestoreV2_1*
_output_shapes
:*
_class

loc:@b*
T0*
validate_shape(*
use_locking(
8
save/restore_shardNoOp^save/Assign^save/Assign_1
-
save/restore_allNoOp^save/restore_shard"<
save/Const:0save/Identity:0save/restore_all (5 @F8"c
trainable_variablesLJ
"
W:0W/AssignW/read:02zeros:0
$
b:0b/Assignb/read:02	zeros_1:0"Y
	variablesLJ
"
W:0W/AssignW/read:02zeros:0
$
b:0b/Assignb/read:02	zeros_1:0